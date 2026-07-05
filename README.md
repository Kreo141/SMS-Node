# Problem it fixes

Many people use more than one phone number—one for banking and accounts, another for work, and another for personal use. But day to day, most people only carry one phone: the one with better battery life, the newer device, or simply the one that’s more convenient.

That convenience comes with a cost: **SMS messages still go to the phone that “owns” the SIM**. So when that other phone is left at home, in a bag, or turned off, important messages can be missed—especially:

**OTP / verification codes** for logins and payments

→ you can’t sign in, can’t approve transactions, and get locked out at the worst time

**Messages from friends, family, or colleagues**

→ delayed replies, missed updates, and unnecessary friction in daily communication

This is a frustrating, common problem—and it keeps happening because SMS is tied to a specific device, while people’s lives aren’t.

**SMS-Node fixes this by forwarding incoming SMS from your “secondary phone” to the phone you actually use.**

# Features

### Can forward to:

- SMS
- Telegram
- Discord (Using webhook - easy to set up than bot)
- PC (Local only)

### Function:

- Allows **forward to single or multiple recepient**
- SMS Fallback if failed to send (Only if enabled)
    - Make a retry interval
        - If still fail after the last one → Send to Telegram or Discord, or both **(ONLY IF ENABLED)**
- Auto connection if already authorized (PC)
