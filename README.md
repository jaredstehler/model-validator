jaxrs-model-validator
=====================

A light-weight validation framework for Java; meant to be useful for a web service wanting to return a rich response containing general validation failures as well as specific field names which have failed validation.

This framework integrates with Google Guice, such that your objects which contain validation methods can inject dependencies on things to assist in the validation process (e.g. UserRepository, to enforce uniqueness constraint on email addresses).

Overview

The framework consists of an annotation used to define a method which can validate a specific object type (@Validates), and a driver class which finds those methods on init and can validate any object (Validators).

Usage

