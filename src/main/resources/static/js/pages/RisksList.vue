<template>
    <v-container>
        <v-layout align-space-around justify-start column>
            <risk-row v-for="risk in sortedRisks"
                      :key="risk.id" :risk="risk"/>
        </v-layout>
    </v-container>
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
        computed: mapGetters(['sortedRisks'])
    }
</script>

<style>

</style>