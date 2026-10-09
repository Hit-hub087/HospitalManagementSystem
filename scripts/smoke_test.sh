#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
OUT_DIR="$ROOT_DIR/out"

rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

javac -d "$OUT_DIR" $(find "$ROOT_DIR/src" -name "*.java") $(find "$ROOT_DIR/tests" -name "*.java")

java -cp "$OUT_DIR" InputValidatorTest
java -cp "$OUT_DIR" AppointmentServiceTest
java -cp "$OUT_DIR" DaoIntegrationTest

echo "Smoke tests completed successfully."
