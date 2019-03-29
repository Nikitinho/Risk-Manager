<template>
    <div>
    <div v-if="!profile">Необходимо авторизоваться через
        <a href="/login">Google</a></div>
    <div v-else>
        <div>{{profile.name}}&nbsp;<a href="/logout">Выйти</a></div>
        <risks-list :risks="risks" />
        </div>
    </div>
</template>

<script>
    import RisksList from 'components/risks/RisksList.vue'
    export default {
        components: {
            RisksList
        },
        data() {
            return {
                risks: frontendData.risks,
                profile: frontendData.profile
            }
        }
    }
</script>

<style>

</style>