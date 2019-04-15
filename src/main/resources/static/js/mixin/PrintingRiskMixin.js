import pdfMake from 'pdfMake/build/pdfmake.min'
import pdfFonts from 'pdfmake/build/vfs_fonts'
import JSZip from 'jszip'

export default {
    methods: {
        savePDF(risk) {
            pdfMake.vfs = pdfFonts.pdfMake.vfs

            let content = [
                {
                    text: `Risk id: ${risk.id}`,
                    style: 'header'
                },
                {
                    text: `Risk title: ${risk.text}`,
                    style: 'default'
                }
            ]

            // Don't need 'id' and 'text' cause these properties are explicitly added to content array
            // Property/field 'Responsible users' needs manual parsing
            // Comments section is not included in .pdf file
            const keys = Object.keys(risk).filter(key =>
                (String)(key) !== 'id' &&
                (String)(key) !== 'text' &&
                (String)(key) !== 'comments' &&
                (String)(key) !== 'responsible')

            keys.forEach(function (key) {
                if (risk[key] !== null && risk[key] !== undefined && risk[key] !== '') {
                    let text = String(key).replace( /([A-Z])/g, " $1" );
                    let sentenceCaseText = text.charAt(0).toUpperCase() + text.slice(1);
                    content.push ({
                        text: `${sentenceCaseText}: ${risk[key]}`,
                        style: 'default'
                    })
                }
            })

            let responsibleUsers = risk['responsible']

            if(responsibleUsers !== null && responsibleUsers !== undefined && responsibleUsers.length > 0) {
                let usersEmails = []
                responsibleUsers.forEach(user => usersEmails.push(user.email))
                content.push({
                    text: `Responsible users: ${usersEmails.join(', ')}`,
                    style: 'default'
                })
            }

            let result = {
                content,
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

            pdfMake.createPdf(result).download(`Risk${risk.id}`)
        },
        async saveAllPDF(risk, projectId) {
            pdfMake.vfs = pdfFonts.pdfMake.vfs

            let content = [
                {
                    text: `Risk id: ${risk.id}`,
                    style: 'header'
                },
                {
                    text: `Risk title: ${risk.text}`,
                    style: 'default'
                }
            ]

            // Don't need 'id' and 'text' cause these properties are explicitly added to content array
            // Property/field 'Responsible users' needs manual parsing
            // Comments section is not included in .pdf file
            const keys = Object.keys(risk).filter(key =>
                (String)(key) !== 'id' &&
                (String)(key) !== 'text' &&
                (String)(key) !== 'comments' &&
                (String)(key) !== 'responsible')

            keys.forEach(function (key) {
                if (risk[key] !== null && risk[key] !== undefined && risk[key] !== '') {
                    let text = String(key).replace( /([A-Z])/g, " $1" );
                    let sentenceCaseText = text.charAt(0).toUpperCase() + text.slice(1);
                    content.push ({
                        text: `${sentenceCaseText}: ${risk[key]}`,
                        style: 'default'
                    })
                }
            })

            let responsibleUsers = risk['responsible']

            if(responsibleUsers !== null && responsibleUsers !== undefined && responsibleUsers.length > 0) {
                let usersEmails = []
                responsibleUsers.forEach(user => usersEmails.push(user.email))
                content.push({
                    text: `Responsible users: ${usersEmails.join(', ')}`,
                    style: 'default'
                })
            }

            let result = {
                content,
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

            let zip = new JSZip()

            // let pdfDocDegenerator
            const pdfDocGenerator = pdfMake.createPdf(result)

            // console.log(pdfDocDegenerator)
            console.log(typeof pdfDocGenerator)

            // await pdfDocGenerator.getBlob((blob) => {
            //     console.log(risk.id)
            //     zip.file(`Risk${risk.id}`, blob, {binary : true, compression : "DEFLATE"})
            // });
            //
            // console.log(zip)
            //
            // await zip.generateAsync({type:"blob"})
            //     .then(function(content) {
            //         saveAs(content, `Project${projectId}`);
            //     });
        }
    }
}