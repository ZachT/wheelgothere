import cherrypy
import urllib2
import json

client_id     = "EXMUKHPHJNF0RCOH14WZNXZL13VVOCK52P1KACWYCCW3SJTN"
client_secret = "VHPJJJY02KMFEGEWJKUDYRUHJ5LJWQNLKSBCUHVLJD0KNNS2"

class Root(object):
    @cherrypy.expose
    def getPlaces(self, lat, lng):        
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
            name = place['name']
            lat  = place['location']['lat']
            lng  = place['location']['lng']
            cat  = place['categories']

            icon = ''
            if len(cat):
                icon = cat[0]['icon']
    
            places.append({'name':name, 'lat':lat, 'lng':lng, 'icon':icon})
        
        return json.dumps(places)

cherrypy.quickstart(Root(), '/')
