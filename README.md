receipt-schema
==============

A schema to encode restaurant receipt info in a URL for a QR code, and an Android app which works with it

Schema
------

Something which is compact and describes the essentials of a receipt, for easy import into Quicken or easy bill splitting.

App
---

This will hook onto URLs starting with receipt:// and decode the portion after it. Should probably contain a bill splitter to be practically useful.