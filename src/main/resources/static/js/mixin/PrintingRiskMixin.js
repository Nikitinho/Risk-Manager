import pdfMake from 'pdfMake/build/pdfmake.min'
import pdfFonts from 'pdfmake/build/vfs_fonts'

export default {
    methods: {
        savePDF(risk) {
            pdfMake.vfs = pdfFonts.pdfMake.vfs

            let content = {
                content: [
                    {
                        text: `Risk id: ${risk.id}`,
                        style: 'header'
                    },
                    {
                        text: `Risk title: ${risk.text}`,
                        style: 'default'
                    },
                    {
                        text: `Risk description: ${risk.description}`,
                        style: 'default'
                    },
                    {
                        text: `Risk category: ${risk.category}`,
                        style: 'default'
                    },
                    {
                        text: `Risk causes: ${risk.causes}`,
                        style: 'default'
                    },
                    {
                        text: `Description of the consequences: ${risk.consequences}`,
                        style: 'default'
                    },
                    {
                        text: `Responsible people: ${risk.responsible}`,
                        style: 'default'
                    },
                    {
                        text: `Risk status: ${risk.status}`,
                        style: 'default'
                    },
                    {
                        text: `Risk impact: ${risk.impact}`,
                        style: 'default'
                    },
                    {
                        text: `Risk probability: ${risk.probability}`,
                        style: 'default'
                    }
                ],
                styles: {
                    header: {
                        fontSize: 18,
                        bold: true
                    },
                    default: {
                        fontSize: 14
                    }
                }
            }
            pdfMake.createPdf(content).download(`Risk${risk.id}`)
        }
    }
}