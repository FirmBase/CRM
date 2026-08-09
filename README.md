# CRM

## Description

This is simple application to track leads or order status in small organization. This was build for [HLBS Tech](https://hlbstech.com/).

This application has email notification that emails everyday for reminder to be in touch with lead.

## Setup

1. Setup DBMS (for example MySQL or MariaDB).
	* Create schema:
		> CREATE SCHEMA `hlbs`;

2. Edit configuration file `application.properties`.
	* Set all mail property with correct values to set up email notification and verification.

3. Open project or run with maven project.

4. Register a user and change status its role to ADMIN, ADMIN has a right to manage users.
	> UPDATE hlbs_erp.users SET hlbs_erp.users.role = 'ADMIN' WHERE hlbs_erp.users.email = '<USER_E-MAIL>';
