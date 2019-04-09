<template>
    <v-container fluid>
        <v-layout align-space-around justify-start wrap>
            <slot v-for="(board, index) in boards">
            <v-flex :xs4="!$vuetify.breakpoint.mdAndDown" :xs12="$vuetify.breakpoint.mdAndDown">
                <v-card class="my-1 mx-1">
                    <v-card-title primary class="headline">{{ board.name }}
                        <v-spacer></v-spacer>
                    <v-btn flat icon @click="() => openBoards.splice(index, 1, !openBoards[index])">
                        <v-icon>arrow_drop_down</v-icon>
                    </v-btn></v-card-title>
                    <slot v-if="openBoards[index]">
                        <v-divider></v-divider>
                    <v-card-text>
                        <span>text will be here</span>
                    </v-card-text>
                    </slot>
                </v-card>
            </v-flex>
            </slot>
            <v-flex shrink :xs4="!$vuetify.breakpoint.mdAndDown" :xs12="$vuetify.breakpoint.mdAndDown" text-xs-center>
                <v-card class="my-1 mx-1">
                    <v-card-title primary class="headline">Create your own board
                        <v-spacer></v-spacer>
                        <v-btn color="success" @click="addNewBoard">
                            <v-icon>add</v-icon>
                        </v-btn>
                    </v-card-title>
                    <v-card-text>
                        <v-text-field placeholder="Type your board's name"
                        v-model="newBoard.name">
                        </v-text-field>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapActions, mapGetters } from 'vuex'
    export default {
        data() {
            return {
                newBoard: { name: '' },
                boards: [],
                openBoards: []
            }
        },
        computed: {
            ...mapGetters(['getBoards'])
        },
        created () {
            this.boards = this.getBoards
            if (this.boards && this.boards.length > 0) {
                this.openBoards = new Array(this.boards.length).fill(false)
            }
        },
        methods: {
            ...mapActions(['addBoardAction']),
            addNewBoard() {
                if (!this.newBoard.name) { return; }
                this.addBoardAction(this.newBoard)
                this.newBoard.name = ''
            }
        }
    }
</script>