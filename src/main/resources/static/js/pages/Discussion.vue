<template>
    <v-container fluid>
        <v-layout align-space-around justify-start wrap>
            <slot v-for="(board, index) in getBoards">
            <v-flex :xs4="!$vuetify.breakpoint.mdAndDown" :xs12="$vuetify.breakpoint.mdAndDown">
                <v-card class="my-1 mx-1 px-2">
                    <v-card-title primary class="headline">
                        <span style="display:block; width: 70%; word-wrap:break-word;">{{ board.name }}</span>
                        <v-spacer></v-spacer>
                    <v-btn flat icon @click="() => showContent(index)">
                        <v-icon>arrow_drop_down</v-icon>
                    </v-btn>
                        <v-btn flat icon @click="() => addItem(index)">
                            <v-icon>add</v-icon>
                        </v-btn>
                    </v-card-title>
                    <slot v-if="newItem.board && newItem.board.id === openBoards[index].id">
                        <v-divider></v-divider>
                        <v-flex xs12>
                            <v-select
                                    :items="boardItems"
                                    placeholder="Message type"
                                    v-model="newItem.type">
                            </v-select>
                        </v-flex>
                        <slot v-if="newItem.type === 'Текстовое сообщение'">
                            <v-flex xs12>
                                <v-divider></v-divider>
                            </v-flex>
                            <v-flex xs12>
                                <v-textarea placeholder="Message"
                                            v-model="newItem.messageText">
                                </v-textarea>
                            </v-flex>
                            <v-flex xs12 text-xs-center>
                            <v-btn color="success" @click="saveItem">
                                <v-icon>save</v-icon>
                            </v-btn>
                            </v-flex>
                        </slot>
                    </slot>
                    <slot v-if="openBoards[index].isShown">
                        <v-flex xs12>
                            <v-list v-if="board.items && board.items.length > 0">
                                <template v-for="item in board.items">
                                    <v-divider></v-divider>
                                    <v-list-tile avatar
                                                 @click="">
                                        <v-list-tile-avatar v-if="item.author">
                                        <v-img class="elevation-6"
                                               :src="item.author.userpic">
                                        </v-img>
                                        </v-list-tile-avatar>

                                        <v-list-tile-content>
                                            <slot v-if="item.type = 'MESSAGE'">
                                            <v-list-tile-title v-html="`${item.messageText}`"></v-list-tile-title>
                                            </slot>
                                        </v-list-tile-content>
                                    </v-list-tile>
                                </template>
                            </v-list>
                            <slot v-else>
                                <v-divider></v-divider>
                                <v-card-text>
                                    <span>Nothing to show here</span>
                                </v-card-text>
                            </slot>
                        </v-flex>
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
    import BoardItem from 'domain/BoardItem'
    export default {
        data() {
            return {
                newBoard: { name: '' },
                boards: [],
                openBoards: [],
                newItem: {}
            }
        },
        computed: {
            ...mapGetters(['getBoards', 'boardItems'])
        },
        created () {
            this.getBoards.forEach(board => this.openBoards.push({id: board.id, isShown: false}))
        },
        watch: {
            getBoards(newValue, oldValue) {
                if (newValue.length > oldValue.length) {
                    console.log('value has increased')
                    const value = newValue[newValue.length - 1]
                    this.openBoards.push({id: value.id, isShown: false})
                } else if (newValue.length < oldValue.length) {
                    console.log('value has decreased')
                }
            }
        },
        methods: {
            ...mapActions(['addBoardAction', 'addBoardItemAction']),
            addNewBoard() {
                if (!this.newBoard.name) { return; }
                this.addBoardAction(this.newBoard)
                this.newBoard.name = ''
            },
            showContent(index) {
                this.openBoards[index].isShown = !this.openBoards[index].isShown
            },
            addItem(index) {
                this.newItem = new BoardItem({}, this.openBoards[index].id)
            },
            saveItem() {
                this.addBoardItemAction(this.newItem)
                this.newItem = new BoardItem()
            }
        }
    }
</script>