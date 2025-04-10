# Spring-AI Routing Workflow Pattern

This project implements the Routing workflow pattern as described in [Building Effective Agents](https://www.anthropic.com/research/building-effective-agents) by Anthropic. 
The pattern enables intelligent routing of inputs to specialized handlers based on content classification.
A Single Shot Prompt Pattern is also employed.

The example uses a local Ollama Server to run an LLM, and llama3.2 is the LLM used. The code will load
the right model into the server, if its not already loaded, but this can take time and the Spring App
may time out. Faster to load it via the Ollams server first.
This could be replaced with an OpenAI or Anthropid model, run externally, and can be configured in the application.properties. You will need to uncomment the appropriate Spring Starter in POM.xml.

The 3 examples are taken from Spring-AI Examples Git repository.

The example provides customer support issue resolution from tickets sent by users. The tickets
provide a Subject and a Message as would be received via Email.

The tickets are examined and sent to, routed to, a specialist team for resolutions.
Four teams are provided in the example: technical, account, product, billing.
The process follow two Steps
1) determine the right teams to process the ticket
2) Route the issue to the team that has its own specified resolution process.

The application provides two ways to use it:
1) API
2) GUI

You will note that the roiuting is sometimes not what you would expect and suggests
the prompt pattern needs more refinement.
A Single Shot Pattern has been employed for the routing, and may need more tuning.
Also the team names may need to be bmore explicitly  defined to improve routing.

## Building and running

The code works with Spring-AI version: 1.0.0.M6

First start an Ollama Server.
Load llama3.2:latest

```shell
./mvnw spring-boot:run
```

To access the WEB GUI: http://localhost:8080/


## Usage Example

## Using the API

```shell 
http :8080/ask/agent ticket="Subject: How to export data? Message: I need to export all my project data to Excel. Ive looked through the docs but cant figure out how to do a bulk export. Is this possible? If so, could you walk me through the steps?  Best regards, Mike"
```

```shell 
http :8080/ask/agent ticket="Subject: How to export data? Message: I need to export all my project data to Excel but cant figure out how to do bulk export. "
```


## Using the GUI

There are three example buttons that fill the Subject and Message fields. Choose one and then Press the Send button.
Otherwise you can input your own text to see a resolution.

Ticket 1
------------------------------------------------------------
Subject: How to export data?
Message: I need to export all my project data to Excel. I've looked through the docs but can't
figure out how to do a bulk export. Is this possible? If so, could you walk me through the steps?
Best regards,
Mike
------------------------------------------------------------
Output:

Available routes: [technical, account, product, billing]
Routing Analysis:The user is requesting a bulk export of their project data to Excel, indicating a product-related issue. The urgent nature of the request and the need for step-by-step instructions suggest that this is a high-priority technical issue.
Selected route: product
------------------------------------------------------------
Product Support Response:

Thank you for reaching out to us, Mike! We're happy to help you with exporting your project data to Excel.

Yes, it is possible to export data in bulk from our platform. You can use the "Export" feature to achieve this. Here's a step-by-step guide to help you get started:

1. Log in to your account and navigate to the project you want to export.
2. Click on the three dots next to the project name and select "Export".
3. In the export settings, choose the format (e.g., CSV or Excel) and select the fields you want to include.
4. You can also choose to exclude certain fields from the export. Refer to our documentation section on [Exporting Data](https://docs.productname.com/exporting-data) for more information.
5. Click "Export" to download the data in your chosen format.

For bulk exporting, you can also use the "[Data Import/Export Tool](https://docs.productname.com/data-import-export-tool)" which allows you to export multiple projects at once.

Additionally, you might find it helpful to explore our [Excel Integration](https://docs.productname.com/excel-integration) feature, which enables seamless data transfer between our platform and Excel.

If you have any further questions or need more assistance, please don't hesitate to reach out. We're here to help!

Best regards,
