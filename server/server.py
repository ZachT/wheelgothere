import cherrypy
import urllib2
import sqlite3
import json

client_id     = "EXMUKHPHJNF0RCOH14WZNXZL13VVOCK52P1KACWYCCW3SJTN"
client_secret = "VHPJJJY02KMFEGEWJKUDYRUHJ5LJWQNLKSBCUHVLJD0KNNS2"

class Root(object):
    @cherrypy.expose
    def getPlaces(self, lat, lng):        
        conn = sqlite3.connect("wheelgothere.db")
        c    = conn.cursor()

        req = urllib2.Request("https://api.foursquare.com/v2/venues/search?client_id=" + 
                              client_id + "&client_secret=" + client_secret + 
                              "&ll=" + lat + "," + lng)

        response = urllib2.urlopen(req, timeout=10).read()
        response = response.replace("true", "True")
        response = response.replace("false", "False")
        response = eval(response)

        groups   = response['response']['groups']

        for group in groups:
            if group['name'] == 'nearby':
                break
        
        places = []

        for place in group['items']:
            c.execute("INSERT OR IGNORE INTO places (id) VALUES(?)", [place['id']]) 
            conn.commit()

            c.execute("SELECT accessible FROM places WHERE id = ?", [place['id']])
            accessible = int(c.fetchone()[0])

            id   = place['id'].strip('"')
            name = place['name'].strip('"')
            lat  = place['location']['lat']
            lng  = place['location']['lng']
            cat  = place['categories']

            icon = ''
            if len(cat):
                icon = cat[0]['icon']

            print name             
            places.append({'id':id, 'name':name, 'lat':lat, 'lng':lng, 'icon':icon, 'accessible':accessible})

        conn.close()        
        return json.dumps(places)

    @cherrypy.expose
    def ratePlace(self, id, rating):
        conn = sqlite3.connect("wheelgothere.db")
        c    = conn.cursor()
        
        c.execute("UPDATE places SET accessible = ? WHERE id = ?", [int(rating), id.replace('"','')])
        conn.commit()
        conn.close()

conn = sqlite3.connect("wheelgothere.db")
c    = conn.cursor()

c.execute("CREATE TABLE IF NOT EXISTS places (id TEXT UNIQUE, accessible INTEGER DEFAULT 0)")
conn.commit()
conn.close()

cherrypy.quickstart(Root(), '/')
