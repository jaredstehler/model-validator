model-validator
=====================

A light-weight validation framework for Java; meant to be useful for a web service wanting to return a rich response containing general validation failures as well as specific field names which have failed validation.

This framework integrates with Google Guice, such that your objects which contain validation methods can inject dependencies on things to assist in the validation process (e.g. UserRepository, to enforce uniqueness constraint on email addresses).

Overview
--------
The framework consists of an annotation used to define a method which can validate a specific object type (@Validates), and a driver class which finds those methods on init and can validate any object (Validators).

Usage
-----

### Write your Validator methods ###

In any java class, add a validation method, and mark it as such with the @Validates annotation, which declares what type is validated by that method. This method can take one of two forms.

    @Validates(Foo.class)
    public void validateFoo(Foo foo) throws ValidationException {
        /* ... */
    }

The first form takes a single parameter, which is an instance of the class declared in the @Validates annotation. This class can throw instances of ValidationException in order to describe why the given object has been found to be invalid.

    @Validates(Foo.class)
    public void validateFoo(Foo foo, ValidationResult result) {
        result.require("name", "Name", foo.getName());
        result.rejectField("email", "Email is invalid");
        result.reject("This object is invalid...");
    }

The second form accepts a ValidationResult instance, which can be used to reject specific fields or the object as a whole.

### Bind Validators class as Guice Singleton ###

The Validators class should be bound to Guice as a singleton, similar to the following:

    bind(ValidatorConfiguration.class).toInstance(new ValidatorConfiguration("my.base.package"));
    bind(Validators.class).asEagerSingleton();

### Inject Validators ###

Finally, inject the Validators instance into services which need it (e.g. repository objects). Validators.validate(Object) will throw a ValidationException on any errors found, or do nothing on success.

    @Inject Validators validators;
    
    public void save(MyObject myInstance) {
        validators.validate(myInstance);
        db.save(myInstance);
    }
