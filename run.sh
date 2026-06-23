#!/usr/bin/env bash
# ──────────────────────────────────────────────────────────────────────────────
# run.sh  –  Compile and run Employee Project Management System on macOS
# Usage:   chmod +x run.sh && ./run.sh
# ──────────────────────────────────────────────────────────────────────────────
set -e

JAR="lib/mysql-connector-j-9.7.0.jar"
SRC_DIR="src/main/java"
OUT_DIR="out"

if [ ! -f "$JAR" ]; then
  echo "❌  MySQL connector JAR not found at $JAR"
  echo "    Download it from https://dev.mysql.com/downloads/connector/j/"
  echo "    and place the JAR inside the lib/ folder."
  exit 1
fi

echo "📦  Compiling..."
mkdir -p "$OUT_DIR"
find "$SRC_DIR" -name "*.java" | xargs javac -cp "$JAR" -d "$OUT_DIR"

echo "🚀  Running..."
java -cp "$OUT_DIR:$JAR" com.epm.ui.MainMenu
