import sys

from PyQt5.QAxContainer import *
from PyQt5.QtWidgets import QApplication
from PyQt5.QtCore import QCoreApplication
from PyQt5.QtWidgets import QMainWindow
from PyQt5.QtWidgets import *
from src.com.stock.util.mongo_db import *
from src.com.stock.util.indi_interect import *
from src.com.stock.util.indi_interect2 import *
import time
import win32com.client as com
from threading import Thread
import pythoncom
from copy import copy