package com.kdu.ibebackend.constants;

public class EmailTemplate {
    public static String OTP_TEMPLATE_NAME = "OTPTemplate";
    public static String EMAIL_SUCCESS = "Email sent successfully";
    public static String EMAIL_SUBJECT = "We value your feedback";
    public static String EMAIL_TEMPLATE = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Room Review Request</title>
            <style>
            /* CSS for styling the email template */
            body {
              font-family: Arial, sans-serif;
              margin: 0;
              padding: 0;
            }
            
            a {
              text-decoration: none;
              color: white;
            }

            .container {
              max-width: 600px;
              margin: 0 auto;
              padding: 20px;
              border: 1px solid #ccc;
            }

            h1 {
              color: #333;
            }

            p {
              color: #666;
            }

            .btn {
              display: inline-block;
              padding: 10px 20px;
              background-color: #007bff;
              color: #fff;
              text-decoration: none;
              border-radius: 5px;
            }

            .btn:hover {
              background-color: #0056b3;
            }
            </style>
            </head>
            <body>

            <div class="container">
              <h1>Room Review Request</h1>
              <p>We hope you enjoyed your recent stay with us. Your feedback is valuable to us.</p>
              <p>Please take a moment to review your room and share your experience with us.</p>
              <p><a href="%s" class="btn">Review Your Room</a></p>
              <p>If you have any questions or concerns, feel free to contact us at <a href="mailto:info@yourhotel.com">info@yourhotel.com</a>.</p>
              <p>Thank you!</p>
            </div>

            </body>
            </html>
            """;
}
