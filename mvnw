#!/bin/sh
# Maven Wrapper script for Unix-like systems

set -e

# Find the project directory
MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-$(cd "$(dirname "$0")" && pwd -P)}"
export MAVEN_PROJECTBASEDIR

# Wrapper jar location
MAVEN_WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
MAVEN_WRAPPER_PROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"

# Download wrapper if needed
if [ ! -f "$MAVEN_WRAPPER_JAR" ]; then
    echo "Downloading Maven Wrapper..."
    if [ -f "$MAVEN_WRAPPER_PROPERTIES" ]; then
        WRAPPER_URL=$(grep 'wrapperUrl' "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2)
        curl -sL "$WRAPPER_URL" -o "$MAVEN_WRAPPER_JAR"
    fi
fi

# Get distribution URL
if [ -f "$MAVEN_WRAPPER_PROPERTIES" ]; then
    DIST_URL=$(grep 'distributionUrl' "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2)
fi

# Maven home
MAVEN_HOME="$HOME/.m2/wrapper/dists/apache-maven-3.9.6"

# Download and extract Maven if needed
if [ ! -d "$MAVEN_HOME" ]; then
    echo "Downloading Maven..."
    mkdir -p "$HOME/.m2/wrapper/dists"
    curl -sL "$DIST_URL" -o "/tmp/maven.zip"
    unzip -q "/tmp/maven.zip" -d "$HOME/.m2/wrapper/dists"
    rm "/tmp/maven.zip"
fi

# Run Maven
exec "$MAVEN_HOME/bin/mvn" "$@"
