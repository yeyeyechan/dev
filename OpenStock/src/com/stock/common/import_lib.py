import sys
import os
import logging
import logging.config
import xmltodict
import time
import win32com.client as com
from threading import Thread
import pythoncom
from copy import copy
import requests
import urllib.parse as urlparse
from pytimekr.pytimekr import is_red_day
from datetime import date, timedelta
from PyQt5.QAxContainer import *
from PyQt5.QtWidgets import QApplication
from PyQt5.QtCore import QCoreApplication
from PyQt5.QtWidgets import QMainWindow
from PyQt5.QtWidgets import *
from src.com.stock.util.logging.myLogging import *
from src.com.stock.util.mongo_db import *
from src.com.stock.util.indi_interect import *
from src.com.stock.common.openAPI import *
from src.com.stock.util.date import *
from src.com.stock.util.data_check import *

TR_1206_len_counts = 0

myLogger = logging.getLogger("myLogger")
TR_1206_logger = logging.getLogger("TR_1206")


