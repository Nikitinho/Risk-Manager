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
    import {addHandler} from 'util/ws';
    import {getIndex} from 'util/collections'
    export default {
        components: {
            RisksList
        },
        data() {
            return {
                risks: frontendData.risks,
                profile: frontendData.profile
            }
        },
        created() {
            addHandler(data => {
                let index = getIndex(this.risks, data.id)
                if (index > -1) {
                    this.risks.splice(index, 1, data)
                } else {
                    this.risks.push(data)
                }
            })
        }
    }
</script>

<style>

</style>