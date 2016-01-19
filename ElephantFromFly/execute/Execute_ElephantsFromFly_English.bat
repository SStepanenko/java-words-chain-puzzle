@ECHO OFF
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;../out/artifacts/ElephantFromFly_jar/ElephantFromFly.jar

set elephatFromFlyApp=com.gmail.stepanenko.sergey27.elephant_from_fly.ElephantFromFly
set inputWordsFile="InputWordsEnglish.txt"
set vocabularyFile="../data/EnglishVocabulary.txt"
set maxWordsChainLength=100
set timeoutValueMinutes=5
set executionResultFile="ExecutionResult.txt"

java %elephatFromFlyApp% %inputWordsFile% %vocabularyFile% %maxWordsChainLength% %timeoutValueMinutes% >%executionResultFile% 2>&1
