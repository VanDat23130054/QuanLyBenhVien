USE master;
GO

-- 1. Enable SQL Server Authentication Mode (Mixed Mode)
-- Value of 2 sets the server auth mode to Mixed (Windows + SQL Server auth)
EXEC xp_instance_regwrite 
    N'HKEY_LOCAL_MACHINE', 
    N'Software\Microsoft\MSSQLServer\MSSQLServer', 
    N'LoginMode', 
    REG_DWORD, 
    2;
PRINT 'SQL Server Authentication Mode set to Mixed Mode.';
GO

-- 2. Alter the 'sa' login with a strong password, remove password policy constraints if testing, and unlock it
ALTER LOGIN sa WITH PASSWORD = 'YourStrongPassword123!', CHECK_POLICY = OFF;
ALTER LOGIN sa ENABLE;
PRINT 'The "sa" account has been configured, updated with a new password, and enabled.';
GO