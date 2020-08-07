package io.github.jobboard.model

case class JobPost(id: String, details: JobPostDetails)

case class JobPostDetails(title: String, description: String, salary: Double, employmentType: String, employer: String)