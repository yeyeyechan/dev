from pymongo import MongoClient

def make_collection(db_name, collection_name):
    if (db_name is not None and collection_name is not None):
        client_main = MongoClient('127.0.0.1', 27017)
        db = client_main[db_name]
        collection = db[collection_name]
    else:
        return None
    return collection
def update_collection(collection_name, data):
    if ( collection_name is not None):
        try:
            collection_name.update(data, data, upsert=True)
        except:
            print("collection update error   ")
            return
def drop_collection(db_name, collection_name):
    client_main = MongoClient('127.0.0.1', 27017)
    db = client_main[db_name]
    collection = db[collection_name]
    collection.drop()
    return collection


if __name__ ==  "__main__":
    drop_collection("stock_data", "TR_1206")
    '''drop_collection("stock_data", "TR_1206")'''