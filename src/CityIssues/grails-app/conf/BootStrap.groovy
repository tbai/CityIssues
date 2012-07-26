import com.cityissues.models.sec.*
import com.cityissues.models.*

class BootStrap {

    def springSecurityService

    def init = { servletContext ->
        createUsers()
        createIssueTypes()
        //createBroadcasts()
    }

    def createUsers = {
        String password = springSecurityService.encodePassword('tigobai')

        def roles = [:]
        ["ROLE_ADMIN", "ROLE_USER"].each{ authority ->
            def role = SecRole.findByAuthority(authority)
            if(!role){
                role = new SecRole(authority: authority).save()
            }
            roles[authority] = role
        }

        def users = [:]
        [
            [roles:["ROLE_USER","ROLE_ADMIN"],user:[username: 'tiagoxbai@gmail.com', name:"Tiago", email:'tiagoxbai@gmail.com', password: password, enabled: true]],
            [role:["ROLE_USER","ROLE_ADMIN"],user:[username: 'admin@suricatourbano.com.br', email:'admin@suricatourbano.com.br', password: password, enabled: true]]
        ].each { map ->
            def user = SecUser.findByUsername(map.user.username)
            if(!user){
                user = new User(map.user).save()
                map.roles.each{ role ->
                    SecUserSecRole.create user, roles[role], true
                }
            }
        }
    }

    def createIssueTypes = {
        def types = [   "crime":"Segurança", 
                        "transportation":"Trânsito", 
                        "trafficcamera":"Fiscalização Eletrônica", 
                        "info":"Informação / Geral"]
        
        types.each {name, label->
            if(!IssueType.findByName(name)){
                new IssueType(name:name, icon:name, label:label, description:label).save()
            }
        }
    }

    def createBroadcasts = {
        def broadcast = Broadcast.findByName("EPTC_POA")
        
        def portoAlegre
        if(!broadcast){
            portoAlegre = new Address(city:"Porto Alegre", state:"RS", country:"Brazil", formatted:"Porto Alegre - RS, Brazil").save()
            broadcast = new Broadcast(
                name:"EPTC_POA", 
                type:"twitter", 
                query:"from:EPTC_POA -\"Radar móvel\" -\"Radar hoje\"", 
                issueTypeName:"transportation", 
                lifeTime:"6.hours", 
                //addressRegex: /(?i)(avenida|av|av\.|rua|r\.|(?<=na)|(?<=esq\.|esquina com|esq|com a)) (?-i)(de |da |do )?\p{Lu}\p{Ll}+( ((do|da|de) )?\p{Lu}\p{Ll}+)?/,
                addressRegex: /(((?i)(avenida|av\.?|rua|r\.?|estr\.?|estrada|(?<=na)) (?-i)(d(a|o|e)s? )?))\p{Lu}\p{Ll}+( ((do|da|de) )?\p{Lu}\p{Ll}+)?|(?<=((esq|prox|próx)\.?( a| da| à)?|(esquina|com|com (a|à))) )((?i)(avenida|av|av\.|rua|r\.|estr\.|estrada) ((de|da|do)s? )?)?(?-i)\p{Lu}\p{Ll}+( ((do|da|de) )?\p{Lu}\p{Ll}+)?/,
                updatePeriod:"10.minutes",
                address: portoAlegre
            )
            broadcast.save()
        }
        
        broadcast = Broadcast.findByName("Radares - EPTC_POA")
        if(!broadcast){
            if(!portoAlegre)
                portoAlegre = new Address(city:"Porto Alegre", state:"RS", country:"Brazil", formatted:"Porto Alegre - RS, Brazil").save()
                
            broadcast = new Broadcast(
                name:"Radares - EPTC_POA", 
                type:"twitter", 
                query:"from:EPTC_POA \" - Radar\"", 
                method: BroadcastMethod.ONE_FOR_EACH_ADDRESS,
                issueTypeName:"trafficcamera", 
                lifeTime:"12.hours",
                updatePeriod:"12.hours",
                addressRegex: /(((?<=:)[^,]+)|((?<=,)( \p{Lu}(\p{Ll}|\.)+)+))|(?<= corredor da ).+$|((?<= e )\p{Lu}.+$)/,
                maxResults:1,
                address: portoAlegre
            )
            broadcast.save()
        }
        // from:EPTC_POA " - Radar"
        // (?i)(?!(Radar.+:\s))?((avenida|av|av\.|rua|r\.) )?(?-i)\p{Lu}\p{Ll}+( ((do|da|de) )?\p{Lu}\p{Ll}+)?(?=,|\.| e)
    }
    def destroy = {
    }
}
