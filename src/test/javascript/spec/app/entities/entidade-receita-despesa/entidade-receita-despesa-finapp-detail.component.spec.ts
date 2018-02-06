/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FinapplicationTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EntidadeReceitaDespesaFinappDetailComponent } from '../../../../../../main/webapp/app/entities/entidade-receita-despesa/entidade-receita-despesa-finapp-detail.component';
import { EntidadeReceitaDespesaFinappService } from '../../../../../../main/webapp/app/entities/entidade-receita-despesa/entidade-receita-despesa-finapp.service';
import { EntidadeReceitaDespesaFinapp } from '../../../../../../main/webapp/app/entities/entidade-receita-despesa/entidade-receita-despesa-finapp.model';

describe('Component Tests', () => {

    describe('EntidadeReceitaDespesaFinapp Management Detail Component', () => {
        let comp: EntidadeReceitaDespesaFinappDetailComponent;
        let fixture: ComponentFixture<EntidadeReceitaDespesaFinappDetailComponent>;
        let service: EntidadeReceitaDespesaFinappService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinapplicationTestModule],
                declarations: [EntidadeReceitaDespesaFinappDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EntidadeReceitaDespesaFinappService,
                    JhiEventManager
                ]
            }).overrideTemplate(EntidadeReceitaDespesaFinappDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntidadeReceitaDespesaFinappDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntidadeReceitaDespesaFinappService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EntidadeReceitaDespesaFinapp(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.entidadeReceitaDespesa).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
