<template>
    <div>
        <input type="text" placeholder="Write something" v-model="text"/>
        <input type="button" value="Save" @click="save"/>
    </div>
</template>

<script>
    function getIndex(list, id) {
        for (var i = 0; i < list.length; i++) {
            if (list[i].id === id) {
                return i;
            }
        }
        return -1;
    }

    export default {
        props: ['risks', 'riskAttr'],
        data() {
            return {
                text: '',
                id: ''
            }
        },
        watch: {
            riskAttr (newVal, oldVal) {
                this.text = newVal.text;
                this.id = newVal.id;
            }
        },
        methods: {
            save() {
                const risk = { text: this.text };

                if (this.id) {
                    this.$resource('/risk{/id}').update({id: this.id}, risk).then(result =>
                        result.json().then(data => {
                            const index = getIndex(this.risks, data.id);
                            this.risks.splice(index, 1, data)
                            this.text = ''
                            this.id = ''
                        })
                    )
                } else {
                    this.$resource('/risk{/id}').save({}, risk).then(result =>
                        result.json().then(data => {
                            this.risks.push(data);
                            this.text = ''
                        })
                    )
                }
            }
        }
    }
</script>

<style>

</style>