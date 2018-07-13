import pychromecast
import time
import http.server

import http.server
import socketserver
import threading

## chrome.exe <COMPUTERS_IP_ADDRESS>

def server():
    PORT = 8000
    Handler = http.server.SimpleHTTPRequestHandler
    httpd = socketserver.TCPServer(("192.168.2.48", PORT), Handler)
    print("serving at port", PORT)
    httpd.serve_forever()

def chrome():
    chromecasts = pychromecast.get_chromecasts()
    for c in chromecasts:
        print(c.device.friendly_name)
        if c.device.friendly_name == "Portable TV":
            print("Device Found")
            mc = c.media_controller
            
            mc.play_media('http://192.168.2.48:8000/air.mp3', 'audio/mp3')


t = threading.Thread(target=server)
t.daemon = True
t.start()
t1 = threading.Thread(target=chrome)
t1.daemon = True
t1.start()
