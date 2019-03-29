<template>
    <div>
    <risk-form :risks="risks" :riskAttr="risk"/>
    <risk-row v-for="risk in risks"
              :key="risk.id" :risk="risk"
              :editRisk="editRisk"
              :deleteRisk="deleteRisk"
              :risks="risks"/>
    </div>
</template>

<script>
    import RiskRow from 'components/risks/RiskRow.vue'
    import RiskForm from 'components/risks/RiskForm.vue'
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
        methods: {
            editRisk(risk) {
                this.risk = risk
            },
            deleteRisk(risk) {
                this.$resource('/risk{/id}').remove({id: risk.id}).then(result => {
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