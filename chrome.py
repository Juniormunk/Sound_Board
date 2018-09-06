import pychromecast
import time
import http.server
import socketserver
import threading
import os

## chrome.exe <CHROMECAST > <COMPUTERS_IP_ADDRESS> <FILE FOLDER PATH> <FILE PATH>

def root_path():
    return os.path.abspath(os.sep)

def server():
    os.chdir(root_path())
    Handler = http.server.SimpleHTTPRequestHandler
    httpd = socketserver.TCPServer(("192.168.2.48", 8000), Handler)
#    print("serving at port", PORT)
    httpd.serve_forever()

def host(ip_address, port, uuid, model_name, friendly_name):
    return  ip_address, port, uuid, model_name, friendly_name

def chrome():
##    chromecasts = pychromecast.get_chromecasts(timeout=1)
##    for c in chromecasts:
##        print(c.device)
##        if c.device.friendly_name == "Portable TV":
##            print("Device Found")
##            mc = c.media_controller
##            print(c.host, c.port, c.device.uuid, c.device.model_name, c.device.friendly_name)
##            mc.play_media('http://192.168.2.48:8000/air.mp3', 'audio/mp3')
##            time.sleep(5)
##            c.disconnect()
    c=pychromecast._get_chromecast_from_host(host('192.168.2.12' ,8009 ,'d94f0900-ca79-f99a-2ad9-48bebdf53a27', 'Chromecast', 'Portable TV'))
    if c.device.friendly_name == "Portable TV":
 #       print("Device Found")
        mc = c.media_controller
#        print(c.host, c.port, c.device.uuid, c.device.model_name, c.device.friendly_name)
        mc.play_media('http://192.168.2.48:8000/C:/Users/Cameron/air.mp3', 'audio/mp3')

t = threading.Thread(target=server)
t.start()
t1 = threading.Thread(target=chrome)
t1.start()
