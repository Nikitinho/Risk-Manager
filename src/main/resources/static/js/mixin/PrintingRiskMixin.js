import jsPDF from 'jspdf/dist/jspdf.min'

export default {
    methods: {
        // https://www.sitepoint.com/generating-pdfs-from-web-pages-on-the-fly-with-jspdf/
        savePDF(risk) {
            let doc = jsPDF()

            doc.setFont("times");

            doc.setFontSize(20);
            doc.setFontType("bold");
            doc.text(10,10, `Risk id: ${risk.id}`);

            doc.setFontSize(14);
            doc.setFontType("regular");

            doc.text(10,17, `Risk title: ${risk.text}`);
            doc.text(10,24, `Risk description: ${risk.description}`)
            doc.text(10,31, `Risk category: ${risk.category}`)
            doc.text(10,38, `Risk causes: ${risk.causes}`)
            doc.text(10,45, `Description of the consequences: ${risk.consequences}`)
            doc.text(10,52, `Responsible people: ${risk.consequences}`)
            doc.text(10,59, `Risk status: ${risk.status}`)

            doc.save(`risk${risk.id}.pdf`)
        }
    }
}