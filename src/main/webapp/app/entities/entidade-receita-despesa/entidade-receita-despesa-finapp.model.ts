import { BaseEntity } from './../../shared';

export class EntidadeReceitaDespesaFinapp implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string
    ) {
    }
}
