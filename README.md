receipt-schema
==============

A schema to encode restaurant receipt info in a URL for a [QR code](http://en.wikipedia.org/wiki/QR_code), and an Android app which works with it. I don't believe any requirements here would prevent an iOS app from being created.

Schema
------

This should be considered schema version 0.001, or something to that effect.

Something which is compact and describes the essentials of a receipt, for easy import into Quicken or easy bill splitting.

This must be compact to make the QR code as easy to scan as possible. I'm not sure whether the roughly 4000 character limit will matter for restaurant receipts.

I'm writing this with a scheme `receipt://`, although this could work with an HTTP url too. That might be preferable since people who didn't have the app would be directed to a download site, or something web-based.

Example for an order of saag paneer and naan for 9.95

`http://georgeschneeloch.com/rcpt/?i1d=SAAG%20M'ROOM+NAN%20T1&i1p=9.95&t=7.00&u=%24&e=Punjabi%20Dhaba%0AThe%20Indian%20Highway-Side%20Cafe&x=WE%20CATER%20FOR%20PARTIES`

Items are prefixed i(num), where num starts at 1 and increments like most numbers do, per item on the receipt.

Per-item keys:

- i(num)d -- description or title of item
- i(num)p -- price

Other keys:

- t -- tax as a percentage
- u -- currency symbol
- pt -- total already paid. If blank, assumes no payment already made.
- en (for establishment) -- name of establishment
- ed (for establishment) -- descriptive message for establishment, which may include newlines
- d -- date of transaction, as a unix timestamp
- o -- order number
- a -- address of establishment as text. May include phone, email, or physical address
- x -- extra descriptive text at bottom of receipt (ie, "Have a nice day!")

App
---

This will hook onto URLs starting with `receipt://` and decode the portion after it. Should probably contain a bill splitter to be practically useful.