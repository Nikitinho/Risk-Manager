<template>
    <v-layout align-space-around justify-start column>
        <risk-form :risks="risks" :riskAttr="risk"/>
        <risk-row v-for="risk in sortedRisks"
                  :key="risk.id" :risk="risk"
                  :editRisk="editRisk"
                  :deleteRisk="deleteRisk"
                  :risks="risks"/>
    </v-layout>
</template>

<script>
    import RiskRow from 'components/risks/RiskRow.vue'
    import RiskForm from 'components/risks/RiskForm.vue'
    import risksApi from 'api/risks'
    export default {
        props: ['risks'],
        components: {
          RiskRow,
          RiskForm
        },
        data() {
            return {
                risk: null
            }
        },
        computed: {
          sortedRisks() {
              return this.risks.sort((a, b) => -(a.id - b.id))
          }
        },
        methods: {
            editRisk(risk) {
                this.risk = risk
            },
            deleteRisk(risk) {
                risksApi.remove(risk.id).then(result => {
                    if (result.ok) {
                        this.risks.splice(this.risks.indexOf(risk), 1)
                    }
                })
            }
        }
    }
</script>

<style>

</style>