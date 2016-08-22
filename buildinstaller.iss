; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Livelook NetXpress"
#define MyAppVersion "B1.0"
#define MyAppPublisher "GatesAir"
#define MyAppExeName "LiveLook_NetXpress.jar"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{A05BE19A-FAF8-471E-965F-7F4C37CFF5E7}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
DefaultDirName={pf}\{#MyAppPublisher}\{#MyAppName}
DefaultGroupName="LiveLook NetXpress"
LicenseFile=C:\Users\hsubrama\Desktop\Me\Projects\josh\backup\LiveLook_NetExpress-master\LIMITED USE SOFTWARE LICENSE AGREEMENT.rtf
OutputBaseFilename=LivelookNetXpress_Setup_B1.0
SetupIconFile=C:\Users\hsubrama\Desktop\Me\Projects\josh\backup\LiveLook_NetExpress-master\LiveLook\livelook.ico
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "C:\Users\hsubrama\Desktop\Me\Projects\josh\backup\LiveLook_NetExpress-master\LiveLook\dist\LiveLook_NetXpress.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\hsubrama\Desktop\Me\Projects\josh\backup\LiveLook_NetExpress-master\LiveLook\dist\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\livelook.ico" 
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\livelook.ico"; Tasks: desktopicon
Name: "{userappdata}\Microsoft\Internet Explorer\Quick Launch\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\livelook.ico"

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: shellexec postinstall skipifsilent
