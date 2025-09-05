INSERT INTO SAMPLE (X__ID, STATUS, ENUM_VALUE, INPUT_VALUE, LOCAL_DATE, X__INSDATE, X__INSUSER, X__VERSION)
VALUES ('1', 'PROCESSING', 'VALUE_A', 'Test value 1', '2024-06-01 12:00:00', '2024-06-01 12:00:00', 'system', 1),
       ('2', 'DONE', 'VALUE_B', 'Test value 2', '2024-06-02 13:00:00', '2024-06-02 13:00:00', 'system', 1),
       ('3', 'PROCESSING', 'VALUE_C', 'Test value 3', '2024-06-03 14:00:00', '2024-06-03 14:00:00', 'system', 1),
       ('4', 'DONE', 'VALUE_A', 'Test value 4', '2024-06-04 15:00:00', '2024-06-04 15:00:00', 'system', 1),
       ('5', 'PROCESSING', 'VALUE_B', 'Test value 5', '2024-06-05 16:00:00', '2024-06-05 16:00:00', 'system', 1),
       ('6', 'DONE', 'VALUE_C', 'Test value 6', '2024-06-06 17:00:00', '2024-06-06 17:00:00', 'system', 1),
       ('7', 'PROCESSING', 'VALUE_A', 'Test value 7', '2024-06-07 18:00:00', '2024-06-07 18:00:00', 'system', 1),
       ('8', 'DONE', 'VALUE_B', 'Test value 8', '2024-06-08 19:00:00', '2024-06-08 19:00:00', 'system', 1),
       ('9', 'PROCESSING', 'VALUE_C', 'Test value 9', '2024-06-09 20:00:00', '2024-06-09 20:00:00', 'system', 1),
       ('10', 'DONE', 'VALUE_A', 'Test value 10', '2024-06-10 21:00:00', '2024-06-10 21:00:00', 'system', 1);

INSERT INTO SAMPLE_CONTAINER (X__ID, SAMPLE, X__INSDATE, X__INSUSER, X__VERSION)
VALUES ('10', '1', '2024-06-01 12:00:00', 'system', 1),
       ('20', '2', '2024-06-02 13:00:00', 'system', 1),
       ('30', '3', '2024-06-03 14:00:00', 'system', 1),
       ('40', '4', '2024-06-04 15:00:00', 'system', 1),
       ('50', '5', '2024-06-05 16:00:00', 'system', 1),
       ('60', '6', '2024-06-06 17:00:00', 'system', 1),
       ('70', '7', '2024-06-07 18:00:00', 'system', 1),
       ('80', '8', '2024-06-08 19:00:00', 'system', 1),
       ('90', '9', '2024-06-09 20:00:00', 'system', 1),
       ('100', '10', '2024-06-10 21:00:00', 'system', 1);
