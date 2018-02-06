import { BaseEntity } from './../../shared';

export class ContaFinapp implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public saldoInicial?: number,
        public tipoContaId?: number,
        public userId?: number,
    ) {
    }
}
