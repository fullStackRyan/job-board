package io.github.jobboard.database

import doobie.implicits._
import io.github.jobboard.model.{JobPost, JobPostDetails}

object JobQueries {

  def createDb: doobie.Update0 = {
    sql"""
         |CREATE DATABASE IF NOT EXISTS jobsdb
       """
      .update
  }

  def createTable: doobie.Update0 = {
    sql"""
         |CREATE TABLE IF NOT EXISTS jobs (
         |  id TEXT PRIMARY KEY,
         |  details JSON NOT NULL
         |)
       """.stripMargin
      .update
  }

  def getAll: doobie.Query0[JobPost] = {
    sql"""
         |SELECT * FROM jobs
       """.stripMargin
      .query[JobPost]
  }

  def insert(jobPost: JobPost): doobie.Update0 = {
    sql"""
         |INSERT INTO jobs (
         |  id,
         |  details
         |)
         |VALUES (
         |  ${jobPost.id},
         |  ${jobPost.details}
         |)
        """.stripMargin
      .update
  }

  def update(jobPost: JobPost): doobie.Update0 = {
    val id = jobPost.id
    val details = jobPost.details
    sql"""
         |UPDATE jobs
         |SET details = $details
         |WHERE id = $id
       """.stripMargin
      .update
  }

  def searchWithId(id: String): doobie.Query0[JobPost] = {
    sql"""
         |SELECT * FROM jobs
         |WHERE id = $id
         |LIMIT 1
       """.stripMargin
      .query[JobPost]
  }

  def delete(id: String): doobie.Update0 = {
    sql"""
         |DELETE FROM jobs
         |WHERE id=$id
       """.stripMargin
      .update
  }

}
