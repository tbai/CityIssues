#############################################################################################
# DDL
#############################################################################################


#############################################################################################
# CUSTOM PROCEDURES/FUNCTIONS
#############################################################################################

DELIMITER $$

DELIMITER ;

#############################################################################################
# DML
#############################################################################################

START TRANSACTION;
    INSERT INTO sec_user_sec_role (sec_role_id, sec_user_id) VALUES (1, 19);

COMMIT;

#############################################################################################
#                                   DDL  Removals                                           #
#############################################################################################