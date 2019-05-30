# Sometimes it's a README fix, or something like that - which isn't relevant for
# including in a project's CHANGELOG for example
declared_trivial = github.pr_title.include? "#trivial"

# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 500

# Don't let testing shortcuts get into master by accident
fail("fdescribe left in tests") if `grep -r fdescribe specs/ `.length > 1
fail("fit left in tests") if `grep -r fit specs/ `.length > 1

# Handle labels
message "Please add labels to this PR" if github.pr_labels.empty?

can_merge = github.pr_json["mergeable"]
message("This PR cannot be merged yet.", sticky: false) unless can_merge

message "This PR does not have any assignees yet." unless github.pr_json["assignee"]

# Handle milestones
has_milestone = github.pr_json["milestone"] != nil
message("This PR does not refer to an existing milestone", sticky: false) unless has_milestone

# Ignore issues out of PR scope
github.dismiss_out_of_range_messages({
  error: false,
  warning: false,
  message: true,
  markdown: true
})

# Ktlint
checkstyle_dir = "**/reports/ktlint/ktlint*.xml"
Dir[checkstyle_dir].each do |file_name|
  checkstyle_reports.inline_comment = true
  checkstyle_reports.report file_name
end

# Lint
lint_dir = "**/reports/lint-*.xml"
Dir[lint_dir].each do |file_name|
  android_lint.skip_gradle_task = true
  android_lint.filtering = true
  android_lint.report_file = file_name
  android_lint.lint(inline_mode: true)
end

# Unit tests
junit_tests_dir = "**/test-results/**/*.xml"
Dir[junit_tests_dir].each do |file_name|
  junit.parse file_name
  junit.show_skipped_tests = true
  junit.report
end
