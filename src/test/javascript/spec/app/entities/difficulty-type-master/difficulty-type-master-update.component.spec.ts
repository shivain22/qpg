import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { DifficultyTypeMasterUpdateComponent } from 'app/entities/difficulty-type-master/difficulty-type-master-update.component';
import { DifficultyTypeMasterService } from 'app/entities/difficulty-type-master/difficulty-type-master.service';
import { DifficultyTypeMaster } from 'app/shared/model/difficulty-type-master.model';

describe('Component Tests', () => {
  describe('DifficultyTypeMaster Management Update Component', () => {
    let comp: DifficultyTypeMasterUpdateComponent;
    let fixture: ComponentFixture<DifficultyTypeMasterUpdateComponent>;
    let service: DifficultyTypeMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [DifficultyTypeMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DifficultyTypeMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DifficultyTypeMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DifficultyTypeMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DifficultyTypeMaster(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DifficultyTypeMaster();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
