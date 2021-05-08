Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy Bypass -Force
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Unrestricted -Force
$fpath = Get-ChildItem -Path C:\ -Include "DockerCli.exe" -Exclude "*.msi","win*" -Recurse -Force -ErrorAction SilentlyContinue | % { $_.fullname } | Select -First 1
if ($fpath) {'Docker is installed'} else {'Docker is not installed'}
curl -o cm.exe  https://github.com/aerokube/cm/releases/download/1.7.2/cm_windows_amd64.exe
./cm.exe selenoid start --browsers-json browsers.json -f
./cm.exe selenoid-ui start