# xenigma
Java tool that allows to encrypt/decrypt string like Enigma

# Setting up

1. Compile the source code as `xenigma.jar`
2. Put it into a directory named `xenigma`
3. Make a new file `xenigma.bat`/`xenigma.sh` and copy contents listed below
4. Open a console and enter `xenigma`

# xenigma.bat

```
@echo off
java -jar xenigma.jar %*
```

# xenigma.sh

```
#!/bin/sh
java -jar xenigma.jar "$@"
```