#!/usr/bin/env bash

set -o errtrace -o nounset -o pipefail -o errexit

# Goto directory of this script
cd "$(dirname "${BASH_SOURCE[0]}")"

self_check () {
  echo "Executing self-check"
  # Don't fail here, failing later at the end when all shell scripts are checked anyway.
  shellcheck ./ci.sh && echo "Self-check succeeded!" || echo "Self-check failed!"
}

cleaning () {
  echo "Cleaning"
  ./sbt clean
}

unit_tests () {
  echo "Executing unit tests"
  ./sbt coverage test
}

integration_tests () {
  echo "Executing integration tests"
  ./sbt coverage it:test
}

coverage_report () {
  echo "Generating combined coverage report"
  ./sbt coverageReport
}

shell_check () {
  echo "Executing shellcheck against all included shell scripts"
  find . -name "*.sh" -print0 | xargs -n 1 -0 shellcheck
}

self_check
cleaning
unit_tests
integration_tests
coverage_report
shell_check
