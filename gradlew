@@ -1,5 +1,21 @@
#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
@@ -28,7 +44,7 @@ APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS=""
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"
@@ -66,6 +82,7 @@ esac

        CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar


# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
@@ -109,10 +126,11 @@ if $darwin; then
        GRADLE_OPTS="$GRADLE_OPTS \"-Xdock:name=$APP_NAME\" \"-Xdock:icon=$APP_HOME/media/gradle.icns\""
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if [ "$cygwin" = "true" -o "$msys" = "true" ] ; then
        APP_HOME=`cygpath --path --mixed "$APP_HOME"`
CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`

JAVACMD=`cygpath --unix "$JAVACMD"`

# We build the pattern for arguments to be converted via cygpath
@@ -138,19 +156,19 @@ if $cygwin ; then
else
eval `echo args$i`="\"$arg\""
fi

i=`expr $i + 1`
done
case $i in

0) set -- ;;
1) set -- "$args0" ;;
2) set -- "$args0" "$args1" ;;
3) set -- "$args0" "$args1" "$args2" ;;
4) set -- "$args0" "$args1" "$args2" "$args3" ;;
5) set -- "$args0" "$args1" "$args2" "$args3" "$args4" ;;
6) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" ;;
7) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" ;;
8) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" "$args7" ;;
9) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" "$args7" "$args8" ;;
esac
        fi

@@ -159,14 +177,9 @@ save () {
    for i do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;\$s/\$/' \\\\/" ; done
    echo " "
}

APP_ARGS=`save "$@"`

# Collect all arguments for the java command, following the shell quoting and substitution rules
eval set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "\"-Dorg.gradle.appname=$APP_BASE_NAME\"" -classpath "\"$CLASSPATH\"" org.gradle.wrapper.GradleWrapperMain "$APP_ARGS"

# by default we should be in the correct project dir, but when run from Finder on Mac, the cwd is wrong
        exec "$JAVACMD" "$@"