<template>
    <div>
    <i>({{ risk.id }})</i> {{ risk.text }}
    <span style="position: absolute; right: 0">
        <input type="button" value="Edit" @click="edit" />
    <input type="button" value="X" @click="del" />
    </span>
    </div>
</template>

<script>
    export default {
        props: ['risk', 'editRisk', 'deleteRisk', 'risks'],
        methods: {
            edit () {
                this.editRisk(this.risk)
            },
            del () {
                this.deleteRisk(this.risk)
            }
        }
    }
</script>

<style>

</style>