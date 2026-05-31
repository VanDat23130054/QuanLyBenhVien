USE master;
GO

-- Check if the SQL Server login already exists
IF NOT EXISTS (SELECT 1 FROM sys.server_principals WHERE name = N'hospital_admin')
BEGIN
    -- 1. Create the SQL Server Login
    -- Replace 'YourStrongPassword123!' with a highly secure password
    CREATE LOGIN [hospital_admin] WITH PASSWORD = 'YourStrongPassword123!';

    -- 2. Add the login to the 'sysadmin' server role
    -- This grants full, unrestricted access to the entire SQL Server instance
    ALTER SERVER ROLE sysadmin ADD MEMBER [hospital_admin];

    PRINT 'SQL Server admin login created and added to sysadmin role.';
END
ELSE
BEGIN
    PRINT 'The SQL Server login already exists. No action taken.';
END
GO