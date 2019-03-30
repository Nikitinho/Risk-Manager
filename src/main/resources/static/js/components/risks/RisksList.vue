<template>
    <v-layout align-space-around justify-start column>
        <risk-form :riskAttr="risk"/>
        <risk-row v-for="risk in sortedRisks"
                  :key="risk.id" :risk="risk"
                  :editRisk="editRisk"/>
    </v-layout>
</template>

<script>
    import { mapGetters } from 'vuex'
    import RiskRow from 'components/risks/RiskRow.vue'
    import RiskForm from 'components/risks/RiskForm.vue'
    export default {
        components: {
          RiskRow,
          RiskForm
        },
        data() {
            return {
                risk: null
            }
        },
        computed: mapGetters(['sortedRisks']),
        methods: {
            editRisk(risk) {
                this.risk = risk
            }
        }
    }
</script>

<style>

</style>