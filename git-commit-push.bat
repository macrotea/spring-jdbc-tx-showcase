@echo off

:: author: macrotea@qq.com / @Macrotea / macrotea.cn

:: get user input content
set /p repoType= Is it a git@oschina repository? (y or n) :

set /p repoName= Input your repository name :

:: config url
set url=
if /i '%repoType%'=='n' (
	set url=https://github.com/macrotea/%repoName%
)

if /i '%repoType%'=='y' (
	set url=https://git.oschina.net/macrotea/%repoName%.git
)

:: git commit and push
git init
git add .
git commit -m 'init'
echo.
echo Commit is finish ! 
echo.
git remote add origin %url%
git push -u origin master
echo.
echo All is finish ! 
echo.
echo. &pause
exit