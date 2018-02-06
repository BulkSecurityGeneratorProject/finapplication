import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EntidadeReceitaDespesaFinapp } from './entidade-receita-despesa-finapp.model';
import { EntidadeReceitaDespesaFinappPopupService } from './entidade-receita-despesa-finapp-popup.service';
import { EntidadeReceitaDespesaFinappService } from './entidade-receita-despesa-finapp.service';

@Component({
    selector: 'jhi-entidade-receita-despesa-finapp-delete-dialog',
    templateUrl: './entidade-receita-despesa-finapp-delete-dialog.component.html'
})
export class EntidadeReceitaDespesaFinappDeleteDialogComponent {

    entidadeReceitaDespesa: EntidadeReceitaDespesaFinapp;

    constructor(
        private entidadeReceitaDespesaService: EntidadeReceitaDespesaFinappService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entidadeReceitaDespesaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entidadeReceitaDespesaListModification',
                content: 'Deleted an entidadeReceitaDespesa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entidade-receita-despesa-finapp-delete-popup',
    template: ''
})
export class EntidadeReceitaDespesaFinappDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entidadeReceitaDespesaPopupService: EntidadeReceitaDespesaFinappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.entidadeReceitaDespesaPopupService
                .open(EntidadeReceitaDespesaFinappDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
