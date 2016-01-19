@ECHO OFF
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;../out/artifacts/ElephantFromFly_jar/ElephantFromFly.jar

set elephatFromFlyApp=com.gmail.stepanenko.sergey27.elephant_from_fly.ElephantFromFly
set inputWordsFile="InputWordsRussian.txt"
set vocabularyFile="../data/RussianVocabularyVerySmall.txt"
set maxWordsChainLength=17
set timeoutValueMinutes=5
set executionResultFile="ExecutionResult.txt"

java %elephatFromFlyApp% %inputWordsFile% %vocabularyFile% %maxWordsChainLength% %timeoutValueMinutes% >%executionResultFile% 2>&1
