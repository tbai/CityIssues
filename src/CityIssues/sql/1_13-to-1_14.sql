#############################################################################################
# DDL
#############################################################################################
ALTER TABLE issue_type 
    ADD COLUMN can_create bit not null,
    ADD COLUMN show_shortcut bit not null;



#############################################################################################
# CUSTOM PROCEDURES/FUNCTIONS
#############################################################################################

DELIMITER $$

DELIMITER ;

#############################################################################################
# DML
#############################################################################################

START TRANSACTION;
    UPDATE issue_type SET show_shortcut = true;
    UPDATE issue_type SET can_create = true;
    
    INSERT  INTO issue_type (version, name, description, can_create, show_shortcut, file) 
            VALUES (0, 'strike', 'Protestos / Passeatas', true, true, ""),
                   (0, 'congress', 'Governo', false, false, "");
    
    UPDATE issue_type 
        SET show_shortcut = false, can_create = false 
        where name="shopping" or name="trafficcamera";
COMMIT;

#############################################################################################
#                                   DDL  Removals                                           #
#############################################################################################