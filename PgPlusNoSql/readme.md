When decided that a certain core concept needs an extension table, performing the following steps:

* make the core concept model (assume named x) extend class ExtensionModel
* in x's corresponding repository
    * inject MongoExtensionService
    * upon saving/retrieving x(s), use MongoExtensionService to save/retrieve extended fields  



Node: for transactional safety, if pgsql fails a transaction, the mongodb save operation does not have to roll back.

The extension document won't have a parent, it will never be read.