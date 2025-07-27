$maven = "mvn"
$baseDir = "lib"

$dependencies = @(
    @{ file = "flatlaf-demo-1.4.jar";       groupId = "com.formdev";   artifactId = "flatlaf-demo";     version = "1.4" },
    @{ file = "jediterm-pty-2.42.jar";      groupId = "com.jediterm";  artifactId = "jediterm-pty";     version = "2.42" },
    @{ file = "rsyntaxtextarea-2.0.4.1.jar";groupId = "org.fife.ui";   artifactId = "rsyntaxtextarea";  version = "2.0.4.1" }
)

foreach ($dep in $dependencies) {
    $filePath = Join-Path $baseDir $dep.file
    if (-Not (Test-Path $filePath)) {
        Write-Host "❌ 文件不存在: $filePath" -ForegroundColor Red
        continue
    }

    Write-Host "`n📦 Installing $($dep.artifactId) from $filePath ..." -ForegroundColor Cyan

   & $maven install:install-file `
        -Dfile="$filePath" `
        -DgroupId="$($dep.groupId)" `
        -DartifactId="$($dep.artifactId)" `
        -Dversion="$($dep.version)" `
        -Dpackaging=jar
}
