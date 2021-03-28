INSERT INTO course(`id`, `name`, `description`) values (1, 'frontend development', 'Learn UI developement from scratch');
INSERT INTO course(`id`, `name`, `description`) values (2, 'backend development', 'Learn developing RestFul Backend');
INSERT INTO course(`id`, `name`, `description`) values (3, 'Mobile App development', 'Learn developing Mobile apps');


INSERT INTO price(`id`, `base_amount`, `course_id`) values (1, 1899.50, 1);
INSERT INTO price(`id`, `base_amount`, `course_id`) values (2, 1899.50, 1);
INSERT INTO price(`id`, `base_amount`, `course_id`) values (3, 2899.50, 2);
INSERT INTO price(`id`, `base_amount`, `course_id`) values (4, 3899.50, 3);

INSERT INTO pricing_strategy(`id`, `duration_in_months`, `type`, `price_id`) values (1, 6, 'ONE_TIME',1);
INSERT INTO pricing_strategy(`id`, `duration_in_months`, `type`, `price_id`) values (2, 12, 'SUBSCRIPTION',2);
INSERT INTO pricing_strategy(`id`, `duration_in_months`, `type`, `price_id`) values (3, 6, 'ONE_TIME',3);
INSERT INTO pricing_strategy(`id`, `duration_in_months`, `type`, `price_id`) values (4, 6, 'ONE_TIME',4);

INSERT INTO coupon(`id`, `name`) values (1, 'test');
INSERT INTO COUPON_COURSES(`courses_id`,coupons_id) values (1,1);
INSERT INTO discount(`id`, `amount`, `course_id`, `coupon_id`) values (1, 100.0, 1, 1);


INSERT INTO tax_component(`id`, `gst_amount`, `price_id`) values (1, 100, 1);
INSERT INTO tax_component(`id`, `gst_amount`, `price_id`) values (2, 100, 2);
INSERT INTO tax_component(`id`, `gst_amount`, `price_id`) values (3, 100, 3);
INSERT INTO tax_component(`id`, `gst_amount`, `price_id`) values (4, 100, 4);